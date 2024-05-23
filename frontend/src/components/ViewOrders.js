import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
const formSchema = z.object({
  name: z.string(),
  location: z.string(),
  price: z.number(),
  visible: z.boolean(),
  available: z.boolean(),
});

export default function ViewOrders() {
  const [cars, setCars] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [providers, setProviders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;
  const [providerId, setProviderId] = useState("");

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      location: "",
      price: "",
      visible: false,
      available: false,
    },
  });

  const onSubmit = async (values) => {
    try {
      const responseProviders = await asyncApiRequest(
        "PUT",
        `/api/providers/${providerId}`,
        values,
        true
      );
      updateJsonFile();

    } catch (error) {
      console.error("Error updating details: ", error);
    }
  };

  //From CarReader   Changed to FlatMap Providers
  const updateJsonFile = async () => {
    try {
      //check data
      let data = await asyncApiRequest("GET", "/api/cars");

      if (data) {
        // Add img property to each car configuration
        data = data.map((item) => {
          item.configurations = item.configurations.map((config) => {
            config.img = `${item.make.replace(/ /g, "-")}-${item.model.replace(
              / /g,
              "-"
            )}.webp`;
            return config;
          });
          return item;
        });
      }

      
      const allRentals = data.flatMap((car) =>
        car.configurations.flatMap((config) =>
            config.providers.flatMap((provider) =>
                provider.rentals.map((rental) => ({ ...rental, car, config, provider }))
            )
        )
    );
    setProviders(allRentals);
    setCars(data);
} catch (error) {
    console.error("Error updating JSON file:", error);
}
};

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(providers.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);


  let sortedCars = [...cars].sort((a, b) => a.id - b.id);

  const getFilteredAndSortedRentals = (rentals, searchTerm) => {
    return rentals
      .filter(
        (rental) =>
          rental.id.toString() === searchTerm ||
          rental.car.make.includes(searchTerm) ||
          rental.car.model.includes(searchTerm) ||
          rental.config.name.includes(searchTerm) ||
          rental.provider.name.includes(searchTerm) ||
          rental.provider.price.toString().includes(searchTerm) ||
          rental.provider.location.includes(searchTerm) ||
          rental.provider.visible.toString().includes(searchTerm) ||
          rental.provider.available.toString().includes(searchTerm)
      )
      .sort((a, b) => a.id - b.id);
  };

  const filteredAndSortedRentals = getFilteredAndSortedRentals(providers, searchTerm);

  console.log("RENTALS:", filteredAndSortedRentals);


  const carsForCurrentPage = filteredAndSortedRentals.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div
      className={
        " mx-auto p-6  flex flex-col  overflow-auto"
      }
    >
      <div className={"flex-grow overflow-auto"}>
        <h2 className={"text-2x1 font-semibold mb-4"}>Rentals View</h2>
        <Input
          type="String"
          placeholder="Search"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

        <form className={"space-y-2"}>
          <Table>
            <TableCaption></TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">Rental ID</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Make</TableHead>
                <TableHead>Model</TableHead>
                <TableHead>Config Name</TableHead>
                <TableHead>Provider Name*</TableHead>
                <TableHead>Location*</TableHead>
                <TableHead>Price*</TableHead>
                <TableHead>Visible*</TableHead>
                <TableHead>Available*</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {carsForCurrentPage
                .map((rentals, index) => (
                  <TableRow key={index}>
                    <TableCell className="font-medium">{rentals.id}</TableCell>
                    <TableCell>{rentals.startDate} To {rentals.endDate}</TableCell>
                    <TableCell>{rentals.car.make}</TableCell>
                    <TableCell>{rentals.car.model}</TableCell>
                    <TableCell>{rentals.config.name}</TableCell>
                    <TableCell>{rentals.provider.name}</TableCell>
                    <TableCell>{rentals.provider.location}</TableCell>
                    <TableCell>{rentals.provider.price + " kr"}</TableCell>
                    <TableCell>{rentals.provider.visible.toString()}</TableCell>
                    <TableCell>{rentals.provider.available.toString()}</TableCell>

                  </TableRow>
                ))}
            </TableBody>
          </Table>
          <div className={"p-4 bg-white"}>
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious onClick={handlePreviousPage} />
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#" isActive>
                    {currentPage}
                  </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext onClick={handleNextPage} />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        </form>
      </div>
    </div>
  );
}
