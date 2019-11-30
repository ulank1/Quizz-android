package kg.mvvmdordoi.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import kg.mvvmdordoi.fcm.FCMTokenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import kg.mvvmdordoi.App;
import kg.mvvmdordoi.R;
import kg.mvvmdordoi.injection.ViewModelFactory;
import kg.mvvmdordoi.model.get.Category;
import kg.mvvmdordoi.network.UserToken;
import kg.mvvmdordoi.ui.info.InfoListActivity;
import kg.mvvmdordoi.ui.info.news.NewsListActivity;
import kg.mvvmdordoi.ui.info.ort.OrtMainListActivity;
import kg.mvvmdordoi.ui.main.about_us.AboutUsActivity;
import kg.mvvmdordoi.ui.main.dayli_game.DayQuestionActivity;
import kg.mvvmdordoi.ui.main.game.GameFragment;
import kg.mvvmdordoi.ui.main.profile.ProfileFragment;
import kg.mvvmdordoi.ui.main.rating_all.RatingFragment;
import kg.mvvmdordoi.ui.main.send_message.SendMessageActivity;
import kg.mvvmdordoi.ui.notification.NotificationActivity;
import kg.mvvmdordoi.ui.settings.SettingsActivity;
import kg.mvvmdordoi.ui.test.EmpActivity;
import kg.mvvmdordoi.ui.test.TestActivity;
import kg.mvvmdordoi.ui.test.TestAddActivity;
import kg.mvvmdordoi.ui.university.UniversityListActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CustomExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Category>> listDataChild;
    FragmentManager fragmentManager;

    MainViewModel viewModel;
    ArrayList<Category> mainCategory = new ArrayList<>();
    ArrayList<Category> category = new ArrayList<>();
    TextView notCount;
    ImageView notImage;
    BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        App.Companion.setActivity(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);

        viewModel.getMainCategoryList();
        viewModel.getMainCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mainCategory = (ArrayList<Category>) categories;
                viewModel.getCategoryList();
            }
        });

        notCount = findViewById(R.id.not_count);
        notImage = findViewById(R.id.img_not);

        notImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, NotificationActivity.class),406);
            }
        });

        viewModel.getSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer size) {

                if (size != null) {
                    notCount.setText(size.toString());
                }

            }
        });

        viewModel.getCategory().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                category = (ArrayList<Category>) categories;
                setExpandable();
            }
        });

        FCMTokenUtils.INSTANCE.deleteToken(this);
        Log.e("DEVICE_ID",FCMTokenUtils.INSTANCE.getTokenFromPrefs(this));


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavView = findViewById(R.id.bottom_navigation);
        bottomNavView.setOnNavigationItemSelectedListener(navListener);

        showProfile();
        if (getIntent().getBooleanExtra("is_duel",false)){
            bottomNavView.setSelectedItemId(R.id.second_bottom_nav);
        }

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data



        // setting list adapter

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                    if (groupPosition==mainCategory.size()-5){
                        startActivity(new Intent(MainActivity.this, UniversityListActivity.class));
                    }else if (groupPosition==mainCategory.size()-4){
                        startActivity(new Intent(MainActivity.this, OrtMainListActivity.class));
                    }else if (groupPosition==mainCategory.size()-3){
                        startActivity(new Intent(MainActivity.this, NewsListActivity.class));
                    }else if (groupPosition==mainCategory.size()-2){
                        startActivity(new Intent(MainActivity.this, SendMessageActivity.class));
                    }else if (groupPosition==mainCategory.size()-1){
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    }

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if(listDataChild.get(mainCategory.get(groupPosition).getName()).get(childPosition).getName().equals("Инфо")){
                    startActivity(new Intent(MainActivity.this, InfoListActivity.class).putExtra("id", Objects.requireNonNull(listDataChild.get(mainCategory.get(groupPosition).getName())).get(childPosition).getMain_category()));

                }else  {

                    Intent intent = new Intent(MainActivity.this, TestActivity.class);
                    intent.putExtra("title",listDataChild.get(mainCategory.get(groupPosition).getName()).get(childPosition).getName());
                    intent.putExtra("category",listDataChild.get(mainCategory.get(groupPosition).getName()).get(childPosition).getId());

                    startActivity(intent);

                }
                // TODO Auto-generated method stub

                return false;
            }
        });

    }

    private void setExpandable() {
        Log.e("GANDON","GANDON"+category);
        int last_id = 1;
        ArrayList<Category> currentCategory = new ArrayList<>();
        listDataChild = new HashMap<String, List<Category>>();



        for (int i = 0; i<category.size();i++){
            if (i==0){
                last_id = category.get(i).getMain_category();
            }

            if (i==category.size()-1){
                if (last_id == category.get(i).getMain_category()) {

                    currentCategory.add(category.get(i));
                    currentCategory.add(new Category(last_id, "Инфо", "", last_id,null,null));
                    listDataChild.put(getHeader(last_id), getListChild(currentCategory));
                    Log.e("DDDSSAD",currentCategory.toString());
                    currentCategory.clear();
                    last_id = category.get(i).getMain_category();
                } else {

                    currentCategory.add(new Category(last_id, "Инфо", "", last_id,null,null));
                    listDataChild.put(getHeader(last_id), getListChild(currentCategory));
                    currentCategory.clear();

                    last_id = category.get(i).getMain_category();
                    currentCategory.add(category.get(i));
                    currentCategory.add(new Category(last_id, "Инфо", "", last_id,null,null));
                    listDataChild.put(getHeader(last_id), getListChild(currentCategory));
                }
            }else {
                Log.e("asdasd",last_id+" "+category.get(i).getMain_category());
                if (last_id == category.get(i).getMain_category()) {

                    currentCategory.add(category.get(i));
                } else {

                    currentCategory.add(new Category(last_id, "Инфо", "", last_id,null,null));
                    listDataChild.put(getHeader(last_id), getListChild(currentCategory));
                    currentCategory.clear();

                    Log.e("dsfsdfdssfd",listDataChild.get(getHeader(last_id)).toString());

                    last_id = category.get(i).getMain_category();
                    currentCategory.add(category.get(i));
                }
                last_id= category.get(i).getMain_category();
                Log.e("CURRENT",currentCategory.toString());

            }



        }
        mainCategory.add(new Category(-1,getString(R.string.univer),"",-1,null,R.drawable.university));
        mainCategory.add(new Category(-1,getString(R.string.ort),"",-1,null,R.drawable.podgotovka));
        mainCategory.add(new Category(-1,getString(R.string.news),"",-1,null,R.drawable.news));
        mainCategory.add(new Category(-1,getString(R.string.send_message),"",-1,null,R.drawable.napishite_nam));
        mainCategory.add(new Category(-1,getString(R.string.about_us),"",-1,null,R.drawable.o_nas));
        Log.e("STrasd",mainCategory+" "+listDataChild);
        listAdapter = new CustomExpandableListAdapter(this, mainCategory, listDataChild);
        expListView.setAdapter(listAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        App.Companion.setActivity(this);
        viewModel.getNotCount();
    }

    private List<Category> getListChild(ArrayList<Category> category) {

        ArrayList<Category> child = new ArrayList<>();
        for (Category cat:category){
            child.add(cat);
        }

        return child;
    }

    void setChildList(ArrayList<Category> category,int last_id){
        listDataChild.put(getHeader(last_id), category);
    }

    String getHeader(int id){

        String head = "";

        for (Category header:mainCategory){

            if (header.getId()==id){
                head = header.getName();
            }

        }

        return head;

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOut) {
          /*  Intent intent = new Intent(this, SplashScreen.class);
            startActivity(intent);
            UserToken.INSTANCE.clearToken(this);
            finish();*/
          startActivity(new Intent(this, EmpActivity.class));
        }else if (id == R.id.action_lang){
          //  Toast.makeText(this, "Язык", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class),407);
        }else if (id == R.id.action_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK==resultCode&&requestCode==407){
            Intent intent = getIntent();
            startActivity(intent);
            finish();

        }else if (RESULT_OK==resultCode&&requestCode==406){
            bottomNavView.setSelectedItemId(R.id.second_bottom_nav);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    String tag = "";

                    switch (menuItem.getItemId()){
                        case R.id.first_bottom_nav:
                            selectedFragment = new ProfileFragment();
                            tag = "profile";
                            break;
                        case R.id.second_bottom_nav:
                            selectedFragment = new GameFragment();
                            tag = "game";
                            break;
                        case R.id.third:
                            selectedFragment = new DayQuestionActivity();
                            tag = "day";
                            break;
                        case R.id.forth:
                            selectedFragment = new RatingFragment();
                            tag = "rating";
                            break;
                    }

                    assert selectedFragment != null;
                    fragmentManager.beginTransaction().add(R.id.mainLayout,
                            selectedFragment).addToBackStack(tag).commit();

                    return true;
                }
            };




    Fragment fragment;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showProfile(){
        fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLayout, fragment, fragment.getTag()).commit();
    }




}
