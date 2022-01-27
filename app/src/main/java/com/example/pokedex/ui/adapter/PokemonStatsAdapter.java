package com.example.pokedex.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.data.model.Stat;
import com.example.pokedex.databinding.StatItemBinding;

import java.util.List;

public class PokemonStatsAdapter extends RecyclerView.Adapter<PokemonStatsAdapter.PokemonStatsViewHolder>{
    private final List<Stat> mPokemonStats;

    public PokemonStatsAdapter(List<Stat> pokemonStats){
        mPokemonStats = pokemonStats;
    }

    @NonNull
    @Override
    public PokemonStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        StatItemBinding statItemBinding = StatItemBinding.inflate(layoutInflater, parent, false);
        return new PokemonStatsViewHolder(statItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonStatsViewHolder holder, int position) {
        Stat stat = mPokemonStats.get(position);
        holder.bind(stat);
    }

    @Override
    public int getItemCount() {
        return mPokemonStats.size();
    }

    public static class PokemonStatsViewHolder extends RecyclerView.ViewHolder{
        private final StatItemBinding mBinding;

        public PokemonStatsViewHolder(@NonNull StatItemBinding statItemBinding) {
            super(statItemBinding.getRoot());
            mBinding = statItemBinding;
        }

        public void bind(Stat stat){
            mBinding.setItem(stat);
            mBinding.executePendingBindings();
        }
    }
}
