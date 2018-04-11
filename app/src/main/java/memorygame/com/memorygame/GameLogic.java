package memorygame.com.memorygame;

import java.util.Collections;
import java.util.List;

public class GameLogic {

    public static void shuffleImageList(List<Integer> imageList) {
        Collections.shuffle(imageList);
    }

    public static void dealNewCards(List<Integer> imageList, MyBtn[] allBtn) {
        for (int i = 0; i < allBtn.length; i++) {
            allBtn[i].btn.setTag(imageList.get(i));
        }
    }

    public static void disableAllBtns(MyBtn[] allBtn) {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(false);
        }
    }

    public static void enableAllBtns(MyBtn[] allBtn) {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(true);
        }
    }

}
