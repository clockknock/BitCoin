package pri.zhong.bitcoin.bean;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class Block {

    private int id;
    private String content;
    private String hash;
    private int nonce;

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    public Block() {
    }

    public Block(int id, String content, String hash, int nonce) {
        this.id = id;
        this.content = content;
        this.hash = hash;
        this.nonce = nonce;
    }
}
