import React from "react";
import { useLocation } from "react-router-dom";

const ViewTransactions = () => {
  const location = useLocation();
  const { transactions } = location.state || { transactions: [] };

  return (
    <div>
      <h1>Transaction History</h1>
      {transactions.length === 0 ? (
        <p>No transactions available</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Description</th>
              <th>Amount</th>
              <th>Sender</th>
              <th>Receiver</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.id}>
                <td>{new Date(transaction.date).toLocaleString()}</td>
                <td>{transaction.description}</td>
                <td>${transaction.amount}</td>
                <td>{transaction.senderUsername}</td>
                <td>{transaction.receiverUsername}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ViewTransactions;
