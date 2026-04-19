package app;

import util.IO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class App
{
    private Compiler compiler;

    private JFrame frame;
    private JTextField title;
    private JTextArea text;
    private JScrollPane scroll;

    private JButton upload;
    private JButton output;
    private JButton compile;

    private JFileChooser file;
    private FileNameExtensionFilter filter;
    private JFileChooser folder;


    // build the UI and set up listeners
    public App()
    {
        this.compiler = new Compiler();

        // UI Setup
        this.frame = new JFrame("CubeCode");
        this.frame.setSize(800, 600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.upload = new JButton("Upload File");
        this.output = new JButton("Output Location");
        this.compile = new JButton("Compile");

        this.title = new JTextField(10);
        this.text = new JTextArea();
        this.scroll = new JScrollPane(this.text);

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("File Name:"));
        titlePanel.add(this.title);
        titlePanel.add(this.upload);
        titlePanel.add(this.output);
        titlePanel.add(this.compile);

        this.frame.setLayout(new BorderLayout());
        this.frame.add(titlePanel, BorderLayout.NORTH);
        this.frame.add(this.scroll, BorderLayout.CENTER);

        this.file = new JFileChooser();
        this.filter = new FileNameExtensionFilter("CubeCode Files (*.cube)", "cube");
        this.file.setFileFilter(this.filter);

        this.folder = new JFileChooser();
        this.folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.folder.setDialogTitle("MCFUNCTION Output Folder");

        // listener for upload button
        this.upload.addActionListener((ActionEvent _) ->
        {
            this.file = new JFileChooser();

            int res = this.file.showOpenDialog(this.frame);

            if (res == JFileChooser.APPROVE_OPTION)
            {
                File file = this.file.getSelectedFile();

                String name = file.getName();

                try
                {
                    if (file.getName().endsWith(".cube"))
                    {
                        String code = IO.read(file);
                        this.text.setText(code);

                        int dot = name.indexOf(".");

                        if (dot != -1)
                        {
                            name = name.substring(0, dot);
                        }

                        this.title.setText(name);
                    }
                    else
                    {
                        throw new Exception("Only .cube files are supported");
                    }
                }
                catch (Exception e)
                {
                    JOptionPane.showMessageDialog(this.frame,"APP ERROR: " + e.getMessage());
                }
            }
        });

        // listener for output location button
        this.output.addActionListener((ActionEvent _) -> this.folder.showOpenDialog(this.frame));

        // listener for compile button
        this.compile.addActionListener((ActionEvent _) ->
        {
            String code = this.text.getText();

            File out = this.folder.getSelectedFile();

            if (out == null)
            {
                this.folder.showOpenDialog(this.frame);
                out = this.folder.getSelectedFile();
            }

            if (out == null)
            {
                return;
            }

            try
            {
                this.compile(code, out);
                JOptionPane.showMessageDialog(this.frame, "Successfully compiled");
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this.frame, "COMPILE ERROR: " + e.getMessage());
            }
        });

        URL url = getClass().getResource("../../img/logo.png");

        if (url != null)
        {
            ImageIcon icon = new ImageIcon(url);
            this.frame.setIconImage(icon.getImage());
        }
    }

    // show the UI
    public void launch()
    {
        this.frame.setVisible(true);
    }

    // compile the code and write it to the output folder
    public void compile(String code, File folder) throws IOException
    {
        String name = this.title.getText();

        if (name.isEmpty())
        {
            name = "unnamed";
        }
        else
        {
            int dot = name.indexOf(".");

            if (dot != -1)
            {
                name = name.substring(0, dot);
            }

            name += ".mcfunction";
        }

        File out = new File(folder, name);

        // this is where the magic happens, we run the compiler and write the output to a file
        IO.write(this.compiler.run(code), out);
    }
}
