package my.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QuizStart extends AppCompatActivity {
    Fragment fragment1 = new QuizFragment1();
    Fragment fragment2 = new QuizFragment2();
    Fragment fragment3 = new QuizFragment3();
    Fragment fragment4 = new QuizFragment4();
    Fragment fragment5 = new QuizFragment5();
    Fragment selected = null;
    Integer num = 1;
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_start);
        ft.replace(R.id.framelayoutid, fragment1).commitAllowingStateLoss();
    }
    public void setQuiz(View v){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        num++;
        switch (num){
            case 2:
                selected = fragment2;
                break;
            case 3:
                selected = fragment3;
                break;
            case 4:
                selected = fragment4;
                break;
            case 5:
                selected = fragment5;
                break;
            default:
                Intent intent = new Intent(this,QuizDone.class);
                startActivity(intent);
                break;
        }
        ft.replace(R.id.framelayoutid, selected);
        ft.commitAllowingStateLoss();
    }
}