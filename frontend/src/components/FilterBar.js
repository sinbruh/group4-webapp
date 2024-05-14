import React from 'react';
import styles from "@/styles/filter.module.css";
import Locationbox from "@/components/ui/locationbox";
import DatePickerWithRange from "@/components/ui/daterange";
import PricePicker from "@/components/ui/pricePicker";

export default function FilterBar({defaultLocation, defaultStart, defaultEnd}) {
    return (
        <div>
            <section className="flex gap-2 items-center bg-[#ffffff] rounded m-4 p-2">
            <Locationbox defaultValue={defaultLocation} />
            <DatePickerWithRange defaultStart={defaultStart} defaultEnd={defaultEnd} />
            <PricePicker />
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
