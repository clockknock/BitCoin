package pri.zhong.bitcoin.bean;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import pri.zhong.bitcoin.utils.RSAUtils;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
    }

    public Wallet(String name) {
        //存公私钥的文件
        File pubFile = new File(name + ".pub");
        File priFile = new File(name + ".pri");

        //判断公私钥文件是否存在,不存在则创建
        if (!pubFile.exists() || pubFile.length() == 0 ||
                !priFile.exists() || priFile.length() == 0) {
            RSAUtils.generateKeysJS("RSA", priFile.getPath(), pubFile.getPath());
        }

        //读取文件获得公私钥
        publicKey = RSAUtils.getPublicKeyFromFile("RSA", pubFile.getPath());
        privateKey = RSAUtils.getPrivateKeyFromFile("RSA", priFile.getPath());
    }

    public Transaction sendMoney(String receiverPublicKey, String content){
        //将公钥转换为字符串
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        //生成签名
        String signature = RSAUtils.getSignature("SHA256WithRSA", privateKey, content);

        //生成交易对象
        return new Transaction(publicKeyString,receiverPublicKey,content,signature);
    }


}
