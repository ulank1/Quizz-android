package kg.mvvmdordoi.ui.main.game.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kg.mvvmdordoi.R
import kg.mvvmdordoi.ui.game.users.UsersActivity
import kg.mvvmdordoi.ui.main.game.GameFragment
import kotlinx.android.synthetic.main.fragment_game_main.*

/**
 * A simple [Fragment] subclass.
 */
class GameMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        setupViewPager()
    }
    private fun setupViewPager() {

        val adapter = MyFragmentPagerAdapter(activity!!.supportFragmentManager)

      //  val firstFragmet: UsersActivity = UsersActivity()
        val secondFragmet: GameFragment = GameFragment()

       // adapter.addFragment(firstFragmet, "Предложить Игру")
        adapter.addFragment(secondFragmet, "Предложенные игры")

        pager.adapter = adapter

        tabs.setupWithViewPager(pager)

    }
}
