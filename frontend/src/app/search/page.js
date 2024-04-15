import styles from "./search.module.css";
import React from 'react';
import Navigation from "@/components/Navigation";
import {Footer} from "@/components/Footer";
import Image from "next/image";
import carImage from "@/img/cars/BMW-M3.jpg";
import Link from "next/link";
export default function Search() {
    return (
        <>
            <Navigation />
            <section className={styles.breadcrumb}>
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/search">Search</Link>
                </p>
            </section>
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
                    oninput="updateValue('fromPrice', 'fromPriceValue')"
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
                    oninput="updateValue('toPrice', 'toPriceValue')"
                />
                <span id="toPriceValue">1000</span>
                <p className="nokPerDay">
                    <b>NOK/day</b>
                </p>
            </section>
            <section className={styles.hero}>
                <section className={styles.cards}>
                    <div className={styles.card} id={"mercedez"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>mercedez</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"bmw"}>
                        <Image src={carImage} alt="car img" width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>bmw</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"audi"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>audi</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"audi"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>audi</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                </section>
                <div className={styles.expandedCard} id={"expandedCard"}>
                    <Image src={carImage} alt={"car img"} width={300} height={200} />
                    <div className={styles.cardi}>
                        <h2>mercedez</h2>
                        <p>Price: 500 NOK/day</p>
                        <p>Location: Ålesund</p>
                        <p>Owner: John Doe</p>
                        <p>Phone: 12345678</p>
                        <p>Email: matsern@email.com</p>
                        <button>Book</button>
                    </div>
                </div>
            </section>
            <Footer />
        </>
    );
};