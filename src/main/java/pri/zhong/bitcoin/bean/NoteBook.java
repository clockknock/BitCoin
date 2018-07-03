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
            String preHash = "0000000000000000000000000000000000000000000000000000000000000000";
            int nonce = mine(genesis + preHash);
            Block block = new Block(0, genesis, HashUtils.sha256(nonce + genesis + preHash), nonce,
                    preHash);
            nodes.add(block);
        } else {
            throw new RuntimeException("已有其他节点,不能添加创世区块");
        }
        save2Disk();
    }

    public void addNote(String note) {
        int nodesSize = nodes.size();
        if (nodesSize > 0) {
            Block preBlock = nodes.get(nodesSize - 1);
            String preBlockHash = preBlock.getHash();
            int nonce = mine(note + preBlockHash);
            Block block = new Block(
                    nodesSize,
                    note,
                    HashUtils.sha256(nonce + note + preBlockHash),
                    nonce,
                    preBlockHash);
            nodes.add(block);
        } else {
            throw new RuntimeException("未有创世区块,不能添加节点");
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

        for (int i = 0; i < nodes.size(); i++) {
            Block block = nodes.get(i);
            int id = block.getId();
            int nonce = block.getNonce();
            String content = block.getContent();
            String hash = block.getHash();
            String preHash = block.getPreHash();
            String currentHash = HashUtils.sha256(nonce + content + preHash);
            //统一验证每一个区块的hash
            if (!currentHash.equals(hash)) {
                sb.append("id为:").append(id).append("的区块的hash值有问题<br/>");
            }
            //验证除了创世区块外,别的区块的preHash
            if (i != 0) {
                String preBlockHash = nodes.get(i - 1).getHash();
                if(!preBlockHash.equals(preHash)){
                sb.append("id为:").append(id).append("的区块的preHash值有问题<br/>");
                }
            }
        }
        return sb.toString();
    }

}
