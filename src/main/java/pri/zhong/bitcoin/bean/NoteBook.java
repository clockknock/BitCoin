package pri.zhong.bitcoin.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
public class NoteBook {
    private ArrayList<String> nodes;
    private File jsonFile = new File("a.json");

    public NoteBook() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionLikeType nodesType = objectMapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, String.class);
            if (jsonFile.exists() && jsonFile.length() > 0) {
                nodes = objectMapper.readValue(jsonFile, nodesType);
            }else{
                nodes = new ArrayList<>();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGenesis(String genesis) {
        if (nodes.size() == 0) {
            nodes.add(genesis);
        } else {
            throw new RuntimeException("已有其他节点,不能添加创世区块");
        }
        save2Disk();
    }

    public void addNote(String note) {
        if (nodes.size() > 0) {

            nodes.add(note);
        } else {
            throw new RuntimeException("未有创世区块,不能添加节点");
        }
        save2Disk();
    }

    public ArrayList<String> showList() {
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

}
