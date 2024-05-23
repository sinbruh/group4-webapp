import React, { useState, useEffect } from 'react';
import { Slider } from '@/components/ui/slider';
import { cn } from "@/lib/utils"

export default function PricePicker({ defaultValue, setPrice }) {
    const [value, setValue] = useState([defaultValue.min || 0, defaultValue.max || 1000]);

    useEffect(() => {
        if (typeof setPrice === 'function') {
            setPrice({ min: value[0], max: value[1] });
        }
    }, [value, setPrice]);

    return (
        <div className="flex flex-col md:flex-row space-x-4">
            <div className="flex items-center space-x-2">
                <p className="block text-sm font-medium text-gray-700">Min: {value[0]}kr</p>
                <Slider  max={1000} step={1} className={"w-52 max-w-full"} value={value} onValueChange={setValue} />

                <p htmlFor="toPrice" className="block text-sm font-medium text-gray-700">Max: {value[1]}kr</p>
            </div>
        </div>

    );
}

