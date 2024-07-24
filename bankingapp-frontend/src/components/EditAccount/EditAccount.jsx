import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const EditAccount = () => {
    const { accountId } = useParams();
    const [account, setAccount] = useState({
        username: '',
        password: '',
        email: '',
        address: '',
        balance: '',
        accountNumber: ''
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        axios.get(`http://localhost:8080/accounts/${accountId}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
            .then(response => {
                setAccount(response.data);
                setLoading(false);
            })
            .catch(err => {
                setError('Failed to fetch account details');
                setLoading(false);
            });
    }, [accountId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAccount({
            ...account,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        console.log("hereeeee")
        console.log(token)
        axios.put(`http://localhost:8080/accounts/edit-account/${accountId}`, account, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })
            .then(response => {
                navigate('/manage-users');
            })
            .catch(err => {
                setError('Failed to update account' + err);
            });
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>Edit Account</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input
                        type="text"
                        name="username"
                        value={account.username}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Password:
                    <input
                        type="password"
                        name="password"
                        value={account.password}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Email:
                    <input
                        type="email"
                        name="email"
                        value={account.email}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Address:
                    <input
                        type="text"
                        name="address"
                        value={account.address}
                        onChange={handleChange}
                    />
                </label>
                <label>
                    Balance:
                    <input
                        type="number"
                        name="balance"
                        value={account.balance}
                        onChange={handleChange}
                    />
                </label>
                <button type="submit">Update</button>
            </form>
        </div>
    );
};

export default EditAccount;
