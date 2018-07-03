package pri.zhong.bitcoin.bean;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import pri.zhong.bitcoin.utils.HashUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class NoteBook {
    private ArrayList<Block> nodes = new ArrayList<>();
    ;
    private File jsonFile = new File("a.json");

    public NoteBook() {
        try {
            if (jsonFile.exists() && jsonFile.length() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                JavaType javaType = objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, Block.class);
                nodes = objectMapper.readValue(jsonFile, javaType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGenesis(String genesis) {
        if (nodes.size() == 0) {
            String hash = HashUtils.sha256(genesis);
            int nonce = mine(genesis + hash);
            Block block = new Block(1, genesis, HashUtils.sha256(nonce + genesis + hash), nonce);
            nodes.add(block);
        } else {
            throw new RuntimeException("已有其他节点,不能添加创世区块");
        }
        save2Disk();
    }

    private int mine(String msg) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String sha256 = HashUtils.sha256(i + msg);
            if (sha256.startsWith("0000")) {
                return i;
            }
        }
        throw new RuntimeException("挖矿失败了,换个数据再来吧");
    }

    public void addNote(String note) {
        if (nodes.size() > 0) {

            String hash = HashUtils.sha256(note);
            int nonce = mine(note + hash);
            Block block = new Block(
                    nodes.size() + 1,
                    note,
                    HashUtils.sha256(nonce + note + hash),
                    nonce);
            nodes.add(block);
        } else {
            throw new RuntimeException("未有创世区块,不能添加节点");
        }
        save2Disk();
    }

    public ArrayList<Block> showList() {
        return nodes;

    }

    private void save2Disk() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(jsonFile, nodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String check() {
        StringBuilder sb = new StringBuilder();
        for (Block node : nodes) {
            String calculatedHash = HashUtils.sha256(node.getContent());
            if (!calculatedHash.equals(node.getHash())) {
                sb.append("节点:").append(node.getId()).append("可能有问题了<br/>");
            }
        }
        return sb.toString();

    }

}
