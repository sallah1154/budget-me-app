package com.example.budgetme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class transactionAdaptor extends RecyclerView.Adapter<transactionAdaptor.TransactionViewHolder> {

    private List<Transactions> transactions = new ArrayList<>();

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transactions transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions != null ? transactions : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView amountTextView;
        private TextView typeTextView;
        private TextView categoryTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.transaction_name);
            amountTextView = itemView.findViewById(R.id.transaction_amount);
            typeTextView = itemView.findViewById(R.id.transaction_type);
            categoryTextView = itemView.findViewById(R.id.transaction_category);
        }

        public void bind(Transactions transaction) {
            nameTextView.setText(transaction.getName());
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            amountTextView.setText(currencyFormat.format(transaction.getAmount()));
            typeTextView.setText(transaction.getType());
            categoryTextView.setText(transaction.getCategory() != null ? transaction.getCategory().getName() : "No Category");
        }
    }
}