package codegen.block;

public enum Property
{
    ;
    public enum Cond
    {
        OFF("false"), ON("true");

        private final String value;

        Cond(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }

    public enum Auto
    {
        OFF("0b"), ON("1b");

        private final String value;

        Auto(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }

    public enum Type
    {
        IMPULSE("command_block"),
        CHAIN("chain_command_block"),
        REPEAT("repeating_command_block");

        private final String value;

        Type(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }

    public enum Dir
    {
        EAST, NORTH, SOUTH, WEST, DOWN, UP;

        @Override
        public String toString()
        {
            return this.name().toLowerCase();
        }
    }
}
