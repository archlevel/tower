package #{package};

#{import}

/**
 * 异常代码定义枚举类
 * @author #{author}  #{date}
 *
 */
public enum #{enumName} #{interface} {
#{enum};

    String code;
    String message;
    String spId;
    int type;
    private #{enumName}(String code,String message,int type,String spId){
        this.code=code;
        this.message=message;
        this.spId=spId;
        this.type=type;
    }
    public String getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }
    public String getValue(){
        return this.getCode();
    }
    public String getSpId(){
        return this.spId;
    }
    public int getType(){
        return this.type;
    }
}