**Minecraft Command Compiler User Guide**

Version 1.0

**1. System Requirements**
   
  Before installing CubeCode, make sure your system meets the following requirements:
  
  Java: Required to run CubeCode. If you have Minecraft Java Edition installed, Java is already on your system.
  
  Minecraft Java Edition: Version 1.21 or later.
  
  ⚠ _Important: This program is currently designed to work exclusively with the Cube
  programming language. It will not compile programs written in other languages._

**3. Installation**
  
  Follow these steps to install CubeCode on your computer:

  1. Navigate to the CubeCode release page on GitHub: https://github.com/og785838-hash/CubeCode

  2. Download the repository as a .zip file.
     
  3. Once downloaded, right-click the zip file and select Extract All, then choose a location for extraction.
     
  4. Open the extracted CubeCode folder and double-click CubeCode.exe to launch the application

  Note: _You do not need to install CubeCode, simply extract the zip and run the exe directly._
  
**3. Setting Up Before Compiling**

  Before you can compile your program, two things must be configured in the CubeCode
  interface:

  **3.1 Output File Name**
  
  Enter a name for your output file in the File Name text box. This will be the name of the
  Minecraft function file that is generated.

  **3.2 Output Location**

  You must point CubeCode to the correct location inside your Minecraft world. Click the output
  location button and navigate to the following path inside your Minecraft installation:
  
  .minecraft/saves/[WorldName]/datapacks/[DataPackName]/data/[Namespace]/function/

  Replace the bracketed placeholders with your actual values:
  
    [WorldName] The name of your Minecraft world.
    
    [DataPackName] The name of your datapack.
    
    [Namespace] The namespace used by your datapack.
  
  Note: _If you are unsure of these values, open Minecraft, load your world, and check the
  datapacks folder inside the world save directory._
  
**4. Saving & Loading Your Program**

  To save a Cube program, click the Save File button and navigate to a desired directory.
  
  There are two ways to load a Cube program into CubeCode:
  
    Upload a file: Click the Upload File button and select your .cube program file from your computer.
    
    Type directly: You can type or paste your Cube program directly into the editor area in the interface.
    
**5. Compiling**

  Once your program is loaded/written and your output settings are configured, the Compile
  button will compile the .cube file into a .mcfunction file in the output directory. CubeCode will
  display a notification indicating whether the compilation was successful or if any errors were
  encountered.
  
  ⚠ _Important: Suggestions and automatic code edits are not currently supported. Review
  your program carefully before compiling._
  
**6. Running Your Program in Minecraft**

  After a successful compilation, your program is ready to run in Minecraft. Follow these steps:
  
    1. Open Minecraft and load the world you configured as the output location.
    
    2. Open the chat and run the following command, replacing the placeholders with your values: /function [Namespace]:[FunctionName]
    
    3. A jagged array of command blocks will be created with a button placed on the program’s launch block.

    4. Simply press the button to execute your program in Minecraft!
    
  Note: _Make sure your datapack is enabled in the world. You can verify this by running /datapack list in the game chat._
  
  Note: _You must refresh the world using the /reload command if you wish to update your program after you load the world._
