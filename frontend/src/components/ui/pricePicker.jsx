import React, { useState, useEffect } from 'react';

export default function PricePicker({defaultValue, setPrice}) {
    const [minPrice, setMinPrice] = useState(defaultValue.min || 0);
    const [maxPrice, setMaxPrice] = useState(defaultValue.max || 1000);

    useEffect(() => {
        if (typeof setPrice === 'function') {
            setPrice({ min: minPrice, max: maxPrice });
        }
    }, [minPrice, maxPrice, setPrice]);


    const handlePriceChange = (event) => {
        const { name, value } = event.target;
        if (name === 'fromPrice') {
            setMinPrice(value);
        } else if (name === 'toPrice') {
            setMaxPrice(value);
        }
    };

    return (
        <div className="flex space-x-4">
            <div className="flex items-center space-x-2">
                <label htmlFor="fromPrice" className="block text-sm font-medium text-gray-700">Min Price:</label>
                <div className="flex space-x-4">
                <input
                    type="range"
                    id="fromPrice"
                    name="fromPrice"
                    value={minPrice}
                    min={0}
                    max={1000}
                    onChange={handlePriceChange}
                    className="mt-1 block w-full"
                />
                <span id="fromPriceValue">{minPrice}</span>
                <p className="text-sm text-gray-500">
                    <b>NOK/day </b>
                </p>
                </div>
            </div>
            <div className="flex items-center space-x-2">
                <label htmlFor="toPrice" className="block text-sm font-medium text-gray-700">Max Price:</label>
                <div className="flex items-center space-x-2">
                <input
                    type="range"
                    id="toPrice"
                    name="toPrice"
                    value={maxPrice}
                    min={0}
                    max={1000}
                    onChange={handlePriceChange}
                    className="mt-1 block w-full"
                />
                <span id="toPriceValue">{maxPrice}</span>
                <p className="text-sm text-gray-500">
                    <b>NOK/day</b>
                </p>
                </div>
                </div>
            </div>
        
    );
}

