import React from 'react';

const clients = [
    {
        id: 1,
        name: 'Vika',
        login: 'Vikusik',
        password: '123123123',
    },
    {
        id: 2,
        name: 'Daunin',
        login: 'Saniil',
        password: '123123123',
    },
    {
        id: 3,
        name: 'Sergey',
        login: 'Bykov',
        password: '123123123',
    },
    {
        id: 4,
        name: 'Dasut',
        login: 'Ryazhaya',
        password: '123123123',
    },
    {
        id: 5,
        name: 'Dasha',
        login: 'Dashutka',
        password: '123123123',
    },
];

export const Profile = ({ currentClient, onClientChange }) => {
    const handleClientChange = (event) => {
        const selectedClientId = parseInt(event.target.value);
        const selectedClient = clients.find((client) => client.id === selectedClientId);
        onClientChange(selectedClient);
    };

    return (
        <div>
            <h2>Информация о пользователе</h2>
            <select value={currentClient ? currentClient.id : ''} onChange={handleClientChange}>
                {clients.map((client) => (
                    <option key={client.id} value={client.id}>
                        {client.login}
                    </option>
                ))}
            </select>
            {currentClient && (
                <div>
                    <h3>Добро пожаловать, {currentClient.name}</h3>
                    <div>
                        <h3>Логин: {currentClient.login}</h3>
                    </div>
                </div>
            )}
        </div>
    );
};