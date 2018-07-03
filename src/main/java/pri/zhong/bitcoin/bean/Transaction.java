package pri.zhong.bitcoin.bean;

import pri.zhong.bitcoin.utils.RSAUtils;

import java.security.PublicKey;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class Transaction {
    //付款方的公钥
    private String senderPublicKey;
    //收款方的公钥
    private String receiverPublicKey;
    //交易信息
    private String content;
    //签名
    private String signature;

    public boolean verify() {
        PublicKey senderPk = RSAUtils.getPublicKeyFromString("RSA", senderPublicKey);
        return RSAUtils.verifyDataJS("SHA256withRSA", senderPk, content, signature);
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderPublicKey='" + senderPublicKey + '\'' +
                ", receiverPublicKey='" + receiverPublicKey + '\'' +
                ", content='" + content + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public Transaction(String senderPublicKey, String receiverPublicKey, String content, String signature) {
        this.senderPublicKey = senderPublicKey;
        this.receiverPublicKey = receiverPublicKey;
        this.content = content;
        this.signature = signature;
    }

    public Transaction() {
    }
}
