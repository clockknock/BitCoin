package pri.zhong.bitcoin.bean;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class Block {

    private int id;
    private String content;
    private String hash;

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

    public Block(int id, String content, String hash) {
        this.id = id;
        this.content = content;
        this.hash = hash;
    }
}
