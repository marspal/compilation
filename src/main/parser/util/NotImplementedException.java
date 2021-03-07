package parser.util;

public class NotImplementedException extends Exception {
    private String _msg;
    public NotImplementedException(String msg){
        this._msg = msg;
    }
}
