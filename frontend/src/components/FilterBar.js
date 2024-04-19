import React from 'react';
import styles from "@/styles/filter.module.css";

export default function FilterBar() {
    return (
        <div>
            <section className={styles.filter}>
                <label>Location:</label>
                <input type="text" name="location" list="locations" />
                <datalist id="locations">
                    <option>Ålesund</option>
                </datalist>
                <label>From:</label>
                <input type="date" name="fromDate" />
                <label>To:</label>
                <input type="date" name="toDate" />
                <label>Min Price:</label>
                <input
                    type="range"
                    id="fromPrice"
                    name="fromPrice"
                    defaultValue={0}
                    min={0}
                    max={1000}
                />
                <span id="fromPriceValue">0</span>
                <p className={styles.nokPerDay}>
                    <b>NOK/day </b>
                </p>
                <label>Max Price:</label>
                <input
                    type="range"
                    id="toPrice"
                    name="toPrice"
                    defaultValue={1000}
                    min={0}
                    max={1000}
                />
                <span id="toPriceValue">1000</span>
                <p className="nokPerDay">
                    <b>NOK/day</b>
                </p>
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
