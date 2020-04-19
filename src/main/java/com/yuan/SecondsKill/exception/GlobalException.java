package com.yuan.SecondsKill.exception;


import com.yuan.SecondsKill.result.CodeMsg;

public class GlobalException extends RuntimeException{
    private static final long serialVersionUID=1;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm=cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
