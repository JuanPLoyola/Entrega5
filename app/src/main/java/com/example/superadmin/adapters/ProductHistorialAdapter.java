package com.example.superadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadmin.R;
import com.example.superadmin.model.ProductInCar;

import java.util.List;

public class ProductHistorialAdapter extends RecyclerView.Adapter<ProductHistorialAdapter.ProductHistorialViewHolder> {

    private Context context;
    private List<ProductInCar> productList;
    private OnItemClickListener onItemClickListener;

    public ProductHistorialAdapter(Context context, List<ProductInCar> productList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.productList = productList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductHistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false);
        return new ProductHistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHistorialAdapter.ProductHistorialViewHolder holder, int position) {
        ProductInCar product = productList.get(position);

        holder.textModel.setText(product.getName());
        holder.imgProduct.setImageResource(product.getImageResId());
        holder.textAmount.setText(String.valueOf(product.getPrice()));


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductHistorialViewHolder extends RecyclerView.ViewHolder {

        TextView textModel, textAmount, textNumberProducts;
        ImageView imgProduct, btnAddProduct, btnMinusProduct;

        CardView containerSingleProduct, btnRemoveCard, btnCopyCard, btnRemove, btnCopy;

        public ProductHistorialViewHolder(@NonNull View itemView) {
            super(itemView);

            textModel = itemView.findViewById(R.id.text_model);
            imgProduct = itemView.findViewById(R.id.img_product);
            textAmount = itemView.findViewById(R.id.text_amount);
        }
    }

    public interface OnItemClickListener {
        void onAddClick(int position);
        void onMinusClick(int position);
        void onRemoveClick(int position);
        void onCopyClick(int position);
    }
}