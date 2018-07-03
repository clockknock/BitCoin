package pri.zhong.bitcoin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.zhong.bitcoin.bean.Block;
import pri.zhong.bitcoin.bean.NoteBook;
import pri.zhong.bitcoin.bean.Transaction;

import java.util.ArrayList;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
@RestController
public class BitCoinController {

    private static NoteBook noteBook;

    static {
        noteBook = new NoteBook();
    }

    @PostMapping("addGenesis")
    public String addGenesis(String genesis) {
        try {
            noteBook.addGenesis(genesis);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
        return "添加成功";
    }

    @PostMapping("/addTransaction")
    public String addTransaction(Transaction transaction) {
        try {
            if (transaction.verify()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String transactionString = objectMapper.writeValueAsString(transaction);
                noteBook.addNote(transactionString);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "添加成功";
    }

    @PostMapping("/addNote")
    public String addNote(String note) {
        try {
            noteBook.addNote(note);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
        return "添加成功";
    }

    @GetMapping("/showList")
    public ArrayList<Block> addNote() {
        return noteBook.showList();
    }


    @GetMapping("/check")
    public String check() {
        String result = noteBook.check();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(result)) {
            return "没有问题";
        }
        return result;
    }


}
