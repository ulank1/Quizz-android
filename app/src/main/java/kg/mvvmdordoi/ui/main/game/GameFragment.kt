package kg.mvvmdordoi.ui.main.game


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kg.mvvmdordoi.R
import kg.mvvmdordoi.injection.ViewModelFactory
import kg.mvvmdordoi.model.get.GameOuter
import kg.mvvmdordoi.ui.game.category.CategoryViewModel
import kg.mvvmdordoi.ui.game.category.CategotryRvAdapter
import kg.mvvmdordoi.ui.game.users.UsersActivity
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_test.rv
import kotlinx.android.synthetic.main.fragment_game.*

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(GameViewModel::class.java)
        setupRv()
        viewModel.users.observe(this, Observer { adapter.swapData(it as ArrayList<GameOuter>) })
        add.setOnClickListener { startActivity(Intent(context!!,UsersActivity::class.java)) }
    }


    private fun setupRv(){
        val layoutManager = GridLayoutManager(context,1)
        rv.layoutManager = layoutManager
        adapter = GameRvAdapter(context!!)
        rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGame()

    }


}
