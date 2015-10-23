package ru.andsp.oou.type;

public enum DataTypes {
    BINARY_DOUBLE {
        @Override
        public boolean isLength() {
            return true;
        }
    }, BINARY_FLOAT {
        @Override
        public boolean isLength() {
            return true;
        }
    },BLOB {
        @Override
        public boolean isLength() {
            return false;
        }
    },CLOB {
        @Override
        public boolean isLength() {
            return false;
        }
    },CHAR {
        @Override
        public boolean isLength() {
            return true;
        }
    },DATE {
        @Override
        public boolean isLength() {
            return false;
        }
    }, LONG {
        @Override
        public boolean isLength() {
            return false;
        }
    },RAW {
        @Override
        public boolean isLength() {
            return true;
        }
    }, NCLOB {
        @Override
        public boolean isLength() {
            return false;
        }
    },NUMBER {
        @Override
        public boolean isLength() {
            return true;
        }
    }, NVARCHAR2 {
        @Override
        public boolean isLength() {
            return true;
        }
    },TIMESTAMP {
        @Override
        public boolean isLength() {
            return false;
        }
    },VARCHAR2 {
        @Override
        public boolean isLength() {
            return true;
        }
    };

    public abstract boolean isLength();
}

