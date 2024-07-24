import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TransactionHistory = () => {
    const [debitTransactions, setDebitTransactions] = useState([]);
    const [creditTransactions, setCreditTransactions] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const token = localStorage.getItem('token');

                const debitResponse = await axios.get('http://localhost:8080/transactions/debit-transactions', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                const creditResponse = await axios.get('http://localhost:8080/transactions/credit-transactions', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                setDebitTransactions(debitResponse.data);
                setCreditTransactions(creditResponse.data);
            } catch (error) {
                setErrorMessage('Error fetching transaction history. Please try again.');
            }
        };

        fetchTransactions();
    }, []);

    return (
        <div>
            <h2>Transaction History</h2>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <div>
                <h3>Debit Transactions</h3>
                {debitTransactions.length === 0 ? (
                    <p>No debit transactions available</p>
                ) : (
                    <ul>
                        {debitTransactions.map(transaction => (
                            <li key={transaction.id}>
                               {transaction.receiverUsername} - {transaction.date} - {transaction.description} - ${transaction.amount}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
            <div>
                <h3>Credit Transactions</h3>
                {creditTransactions.length === 0 ? (
                    <p>No credit transactions available</p>
                ) : (
                    
                    <ul>
                        {creditTransactions.map(transaction => (
                            <li key={transaction.id}>
                               {transaction.senderUsername} - {transaction.date} - {transaction.description} - ${transaction.amount} 
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
};

export default TransactionHistory;
