import React, {useState} from "react";
export const SearchBar = ({ onSearch }) =>{
    const [searchTerm,setSearchTerm] = useState('');

    const handleSearch = () => {
        onSearch(searchTerm);
    };

    return (
        <div>
            <input
                type="text"
                placeholder="Введите название товара"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button className={"button"} onClick={handleSearch}>Поиск</button>
        </div>
    );
};