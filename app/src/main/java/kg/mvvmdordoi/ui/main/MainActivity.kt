package kg.mvvmdordoi.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

import kg.mvvmdordoi.fcm.FCMTokenUtils

import kg.mvvmdordoi.App
import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.Category
import kg.mvvmdordoi.network.Day
import kg.mvvmdordoi.network.Lang
import kg.mvvmdordoi.network.UserToken
import kg.mvvmdordoi.ui.info.InfoListActivity
import kg.mvvmdordoi.ui.info.news.NewsListActivity
import kg.mvvmdordoi.ui.info.ort.OrtMainListActivity
import kg.mvvmdordoi.ui.main.about_us.AboutUsActivity
import kg.mvvmdordoi.ui.main.dayli_game.DayQuestionActivity
import kg.mvvmdordoi.ui.main.game.GameFragment
import kg.mvvmdordoi.ui.main.profile.ProfileFragment
import kg.mvvmdordoi.ui.main.rating_all.RatingFragment
import kg.mvvmdordoi.ui.main.send_message.SendMessageActivity
import kg.mvvmdordoi.ui.notification.NotificationActivity
import kg.mvvmdordoi.ui.settings.SettingsActivity
import kg.mvvmdordoi.ui.test.EmpActivity
import kg.mvvmdordoi.ui.test.RewardedActivity
import kg.mvvmdordoi.ui.test.TestActivity
import kg.mvvmdordoi.ui.test.TestAddActivity
import kg.mvvmdordoi.ui.university.UniversityListActivity
import kg.mvvmdordoi.utils.extension.getDateDot
import kg.mvvmdordoi.utils.extension.getTodayDateDot
import kg.mvvmdordoi.utils.extension.gone
import kg.mvvmdordoi.utils.extension.visible
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var listAdapter: CustomExpandableListAdapter
    lateinit var expListView: ExpandableListView
    var listDataHeader: List<String>? = null
    lateinit var listDataChild: HashMap<String, List<Category>>
    lateinit var fragmentManager: FragmentManager

    lateinit var viewModel: MainViewModel
    var mainCategory = ArrayList<Category>()
    var category = ArrayList<Category>()
    lateinit var notCount: TextView
    lateinit var notImage: ImageView
    lateinit var bottomNavView: BottomNavigationView

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        var selectedFragment: Fragment? = null

        var tag = ""
        Log.e("LOCALE", Locale.getDefault().country)
        if (Lang.get(this) == "1") {
            val locale = Locale("ky")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        } else {
            val locale = Locale("ru")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        }
        when (menuItem.itemId) {
            R.id.first_bottom_nav -> {
                selectedFragment = ProfileFragment()
                tag = "profile"
            }
            R.id.second_bottom_nav -> {
                selectedFragment = GameFragment()
                tag = "game"
            }
            R.id.third -> {
                selectedFragment = DayQuestionActivity()
                tag = "day"
            }
            R.id.forth -> {
                selectedFragment = RatingFragment()
                tag = "rating"
            }
        }

        assert(selectedFragment != null)
        val commit = fragmentManager.beginTransaction().add(
            R.id.mainLayout,
            selectedFragment!!
        ).addToBackStack(tag).commit()

        true
    }


    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        App.activity = this
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        fragmentManager = supportFragmentManager

        if (Day.getFirst(this) == "0") {
            Day.saveFirst(getTodayDateDot(), this)
        } else {
            if (getDateDot(-3, Calendar.DAY_OF_YEAR) == Day.getFirst(this)) {
                showAlert()
            }
        }

        MobileAds.initialize(this,"ca-app-pub-7649587179327452~9914538335")
        var mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        yes.setOnClickListener { finish() }
        no.setOnClickListener { exit.gone() }

        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MainViewModel::class.java)

        viewModel.getMainCategoryList()
        viewModel.mainCategory.observe(this, Observer { categories ->
            mainCategory = (categories as ArrayList<Category>?)!!
            viewModel.getCategoryList()
        })

        notCount = findViewById(R.id.not_count)
        notImage = findViewById(R.id.img_not)

        notImage.setOnClickListener {
            startActivityForResult(
                Intent(
                    this@MainActivity,
                    NotificationActivity::class.java
                ), 406
            )
        }

        viewModel.size.observe(this, Observer { size ->
            if (size != null) {
                notCount.text = size.toString()
            }
        })
        viewModel.getRating()

        viewModel.category.observe(this, Observer { categories ->
            category = (categories as ArrayList<Category>?)!!
            setExpandable()
        })

        FCMTokenUtils.deleteToken(this)
        Log.e("DEVICE_ID", FCMTokenUtils.getTokenFromPrefs(this))


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavView = findViewById(R.id.bottom_navigation)
        bottomNavView.setOnNavigationItemSelectedListener(navListener)

        showProfile()
        if (intent.getBooleanExtra("is_duel", false)) {
            bottomNavView.selectedItemId = R.id.second_bottom_nav
        }

        // get the listview
        expListView = findViewById<View>(R.id.lvExp) as ExpandableListView

        // preparing list data


        // setting list adapter

        // Listview Group click listener
        expListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            if (groupPosition == mainCategory.size - 5) {
                startActivity(Intent(this@MainActivity, UniversityListActivity::class.java))
            } else if (groupPosition == mainCategory.size - 4) {
                startActivity(Intent(this@MainActivity, OrtMainListActivity::class.java))
            } else if (groupPosition == mainCategory.size - 3) {
                startActivity(Intent(this@MainActivity, NewsListActivity::class.java))
            } else if (groupPosition == mainCategory.size - 2) {
                startActivity(Intent(this@MainActivity, SendMessageActivity::class.java))
            } else if (groupPosition == mainCategory.size - 1) {
                startActivity(Intent(this@MainActivity, AboutUsActivity::class.java))
            }

            false
        }

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener { }

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener { }

        // Listview on child click listener
        expListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if (listDataChild[mainCategory[groupPosition].name]!![childPosition].name == "Инфо") {
                startActivity(
                    Intent(this@MainActivity, InfoListActivity::class.java).putExtra(
                        "id",
                        Objects.requireNonNull<List<Category>>(listDataChild[mainCategory[groupPosition].name])[childPosition].main_category
                    )
                )

            } else {

                val intent = Intent(this@MainActivity, TestActivity::class.java)
                intent.putExtra(
                    "title",
                    listDataChild[mainCategory[groupPosition].name]!![childPosition].name
                )
                intent.putExtra(
                    "category",
                    listDataChild[mainCategory[groupPosition].name]!![childPosition].id
                )

                startActivity(intent)

            }
            // TODO Auto-generated method stub

            false
        }

    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Бизге баа бериңиз!")
        builder.setMessage("Хотите оценить Synak Time?")
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Yes") { dialog, which ->
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())
                    )
                )
            }
        }

        builder.setNegativeButton("No") { dialog, which ->
            Day.saveFirst(getTodayDateDot(), this)
            dialog.dismiss()
        }

        builder.show()
    }

    private fun setExpandable() {
        Log.e("GANDON", "GANDON$category")
        var last_id = 1
        val currentCategory = ArrayList<Category>()
        listDataChild = HashMap()



        for (i in category.indices) {
            if (i == 0) {
                last_id = category[i].main_category
            }

            if (i == category.size - 1) {
                if (last_id == category[i].main_category) {

                    currentCategory.add(category[i])
                    currentCategory.add(Category(last_id, "Инфо", "", last_id, null, null))
                    listDataChild[getHeader(last_id)] = getListChild(currentCategory)
                    Log.e("DDDSSAD", currentCategory.toString())
                    currentCategory.clear()
                    last_id = category[i].main_category
                } else {

                    currentCategory.add(Category(last_id, "Инфо", "", last_id, null, null))
                    listDataChild[getHeader(last_id)] = getListChild(currentCategory)
                    currentCategory.clear()

                    last_id = category[i].main_category
                    currentCategory.add(category[i])
                    currentCategory.add(Category(last_id, "Инфо", "", last_id, null, null))
                    listDataChild[getHeader(last_id)] = getListChild(currentCategory)
                }
            } else {
                Log.e("asdasd", last_id.toString() + " " + category[i].main_category)
                if (last_id == category[i].main_category) {

                    currentCategory.add(category[i])
                } else {

                    currentCategory.add(Category(last_id, "Инфо", "", last_id, null, null))
                    listDataChild[getHeader(last_id)] = getListChild(currentCategory)
                    currentCategory.clear()

                    Log.e("dsfsdfdssfd", listDataChild[getHeader(last_id)]!!.toString())

                    last_id = category[i].main_category
                    currentCategory.add(category[i])
                }
                last_id = category[i].main_category
                Log.e("CURRENT", currentCategory.toString())

            }


        }
        mainCategory.add(
            Category(
                -1,
                getString(R.string.univer),
                "",
                -1,
                null,
                R.drawable.university
            )
        )
        mainCategory.add(Category(-1, getString(R.string.ort), "", -1, null, R.drawable.podgotovka))
        mainCategory.add(Category(-1, getString(R.string.news), "", -1, null, R.drawable.news))
        mainCategory.add(
            Category(
                -1,
                getString(R.string.send_message),
                "",
                -1,
                null,
                R.drawable.napishite_nam
            )
        )
        mainCategory.add(Category(-1, getString(R.string.about_us), "", -1, null, R.drawable.o_nas))
        Log.e("STrasd", (mainCategory + " " + listDataChild).toString())
        listAdapter = CustomExpandableListAdapter(this, mainCategory, listDataChild)
        expListView.setAdapter(listAdapter)

    }

    override fun onResume() {
        super.onResume()
        Log.e("DDDDsfdfssfdfds", Lang.get(this)!!)
        if (Lang.get(this) == "1") {
            val locale = Locale("ky")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        } else {
            val locale = Locale("ru")
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)
        }
        App.activity = this
        viewModel.getNotCount()
    }

    private fun getListChild(category: ArrayList<Category>): List<Category> {

        val child = ArrayList<Category>()
        for (cat in category) {
            child.add(cat)
        }

        return child
    }

    internal fun setChildList(category: ArrayList<Category>, last_id: Int) {
        listDataChild[getHeader(last_id)] = category
    }

    internal fun getHeader(id: Int): String {

        var head = ""

        for ((id1, name) in mainCategory) {

            if (id1 == id) {
                head = name
            }

        }

        return head

    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            exit.visible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_logOut) {
            var intent = Intent(this, SplashScreen::class.java)
            startActivity(intent)
            UserToken.clearToken(this)
            finish()
//            startActivity(Intent(this, EmpActivity::class.java))
        } else if (id == R.id.action_lang) {
            //  Toast.makeText(this, "Язык", Toast.LENGTH_SHORT).show();
            startActivityForResult(Intent(this@MainActivity, SettingsActivity::class.java), 407)
        } else if (id == R.id.action_share) {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "https://play.google.com/store/apps/details?id=kg.mvvmdordoi"
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (Activity.RESULT_OK == resultCode && requestCode == 407) {
            val intent = intent
            startActivity(intent)
            finish()

        } else if (Activity.RESULT_OK == resultCode && requestCode == 406) {
            bottomNavView.selectedItemId = R.id.second_bottom_nav
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showProfile() {
        fragment = ProfileFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.mainLayout, fragment, fragment.tag).commit()
    }


}
