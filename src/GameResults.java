public enum GameResults {
    DEAD_HEAT("0:0"),FIRST_WIN("1:0"),SECOND_WIN("0:1"),ALL_WIN("1:1"),HALF_WIN("1/2 : 1/2");
    
    String code;
    GameResults(String s) {
        this.code = s;
    }

    public String getCode() {
        return code;
    }
}

