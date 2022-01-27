package com.example.pokedex.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;
import com.example.pokedex.data.model.NamedApiResource;
import com.example.pokedex.data.model.PokemonImageSource;
import com.example.pokedex.databinding.PokemonItemBinding;
import com.example.pokedex.ui.main.MainFragmentDirections;

import org.jetbrains.annotations.NotNull;

public class NamedApiResourceAdapter extends PagingDataAdapter<NamedApiResource, NamedApiResourceAdapter.NamedApiResourceViewHolder> {

    public NamedApiResourceAdapter(@NotNull DiffUtil.ItemCallback<NamedApiResource> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NamedApiResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        PokemonItemBinding itemBinding =
                PokemonItemBinding.inflate(layoutInflater, parent, false);
        return new NamedApiResourceViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NamedApiResourceViewHolder holder, int position) {
        NamedApiResource item = getItem(position);
        holder.bind(item);
    }

    public static class NamedApiResourceViewHolder extends RecyclerView.ViewHolder {
        private final PokemonItemBinding binding;

        public NamedApiResourceViewHolder(@NonNull PokemonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NamedApiResource item){
            binding.setItem(item);
            binding.setItemImageUrl(PokemonImageSource.getUrl(item));
            setupCardViewClickEventWith(item);
            binding.executePendingBindings();
        }

        private void setupCardViewClickEventWith(NamedApiResource item){
            MainFragmentDirections.ActionToPokemonDetailFragment actionToPokemonDetailFragment
                    = MainFragmentDirections.actionToPokemonDetailFragment();
            actionToPokemonDetailFragment.setPokemonName(item.getName());
            actionToPokemonDetailFragment.setPokemonImageUrl(PokemonImageSource.getUrl(item));
            binding.cardView.setOnClickListener(view -> {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(actionToPokemonDetailFragment);
            });
        }
    }

    public static class NamedApiResourceComparator extends DiffUtil.ItemCallback<NamedApiResource> {
        @Override
        public boolean areItemsTheSame(@NonNull NamedApiResource oldItem,
                                       @NonNull NamedApiResource newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NamedApiResource oldItem,
                                          @NonNull NamedApiResource newItem) {
            return oldItem.equals(newItem);
        }
    }
}
