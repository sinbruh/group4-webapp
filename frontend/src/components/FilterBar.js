import React, { useState, useEffect } from 'react';
import styles from "@/styles/filter.module.css";
import Locationbox from "@/components/ui/locationbox";
import DatePickerWithRange from "@/components/ui/daterange";
import PricePicker from "@/components/ui/pricePicker";

export default function FilterBar({defaultLocation, defaultStart, defaultEnd, setLocation, setDates, setPrice}) {
    const [location, setLocationState] = useState(defaultLocation);
    const [dates, setDatesState] = useState({start: defaultStart, end: defaultEnd});
    const [price, setPriceState] = useState({min: null, max: null});


   

    useEffect(() => {
        setLocation(location);
        setDates(dates);
        setPrice(price);
    }, [location, dates, price]);

    return (
        <div>
            <section className="flex gap-2 items-center bg-[#ffffff] rounded m-4 p-2">
            <Locationbox defaultValue={defaultLocation} setLocation={setLocation} />
            <DatePickerWithRange defaultStart={defaultStart} defaultEnd={defaultEnd} setDates={setDates} />
            <PricePicker defaultValue={price} setPrice={setPrice}/>
            </section>
        </div>

        
    );


    /* function updateValue(sliderid, outputid) {
        var fromprice = document.getelementbyid('fromprice');
        var toprice = document.getelementbyid('toprice');
        var frompricevalue = document.getelementbyid('frompricevalue');
        var topricevalue = document.getelementbyid('topricevalue');

        if (sliderid === 'fromprice' && parseint(fromprice.value) > parseint(toprice.value)) {
            toprice.value = fromprice.value;
        } else if (sliderid === 'toprice' && parseint(toprice.value) < parseint(fromprice.value)) {
            fromprice.value = toprice.value;
        }

        frompricevalue.innertext = fromprice.value;
        topricevalue.innertext = toprice.value;
    } */
}
