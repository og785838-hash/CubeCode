import app.App;

public class CubeCode
{
    public static void main(String[] args)
    {
        System.out.println(CubeCode.credits());
        (new App()).launch();
    }

    private static String credits()
    {
        return """
                CubeCode : Minecraft Command Compiler
                Version  : 1.0
                Authors  : Gabriel Osborn, Henry Perry""";
    }
}
