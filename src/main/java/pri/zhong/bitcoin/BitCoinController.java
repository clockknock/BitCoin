package pri.zhong.bitcoin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pri.zhong.bitcoin.bean.NoteBook;

import java.util.ArrayList;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
@RestController
public class BitCoinController {

    private static NoteBook noteBook;
    static{
        noteBook =new NoteBook();
    }

    @PostMapping("addGenesis")
    public String addGenesis(String genesis){
        try {
            noteBook.addGenesis(genesis);

        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
        return "添加成功";
    }


    @PostMapping("/addNote")
    public String addNote(String note){
        try {
            noteBook.addNote(note);
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
        return "添加成功";
    }

    @GetMapping("/showList")
    public ArrayList<String> addNote(){
        return noteBook.showList();
    }



}
