import React from "react";

export const RemoveFromCartButton = ({productId,onRemoveFromCart}) => {
    const handleClick = () =>{
        onRemoveFromCart(productId);
    }

    return(
        <button onClick ={handleClick} className={"button"}>Удаление из корзины</button>
    )
}