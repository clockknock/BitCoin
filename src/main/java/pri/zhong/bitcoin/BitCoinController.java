package pri.zhong.bitcoin;

import org.springframework.web.bind.annotation.RestController;
import pri.zhong.bitcoin.bean.Block;

import java.util.ArrayList;

/**
 * @author 钟未鸣
 * @date 2018/7/3
 */
@RestController
public class BitCoinController {

    public static void main(String[] args) {
        Block block = new Block();
        block.addGenesis("这是创世");
        block.addNote("信息");
        ArrayList<String> strings = block.showList();
        for (String string : strings) {
            System.out.println(string);
        }
    }

}
