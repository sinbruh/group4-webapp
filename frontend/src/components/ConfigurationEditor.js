import React, { useEffect, useState } from "react";
import { asyncApiRequest } from "@/tools/request";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useForm, Controller } from 'react-hook-form';
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
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
  } from "@/components/ui/form";
  import { zodResolver } from '@hookform/resolvers/zod';
  import { z } from 'zod';
  const formSchema = z.object({
    name: z.string(),
    numberOfSeats: z.number(),
    fuelType: z.string(),
    transmissionType: z.string(),
    
  });

export default function ConfigurationEditor() {
  const [cars, setCars] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [configs, setConfigs] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;
  const [configId, setConfigId] = useState('');

  const form = useForm({
    resolver: zodResolver(formSchema),
        defaultValues: {
        name: '',
        numberOfSeats: '',
        fuelType: '',
        tranmissionType: ''
        
    }
  });

  const onSubmit = async (values)  => {
    
    try {
        const response = await asyncApiRequest("PUT", `/api/configurations/${configId}`, values, true);
        
    

        if (response.ok) {    
           
        } else {
            console.error("Error updating config details: ", response);
           
        }
    } catch (error) {
        console.error("Error updating config details: ", error);
    }
};


  //From CarReader    Changed to FlatMap Configurations  
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

      const allConfigs = data.flatMap((car) =>
        car.configurations.map((config) => ({ ...config, car }))
      );
      setConfigs(allConfigs);
      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    if (currentPage < Math.ceil(configs.length / entriesPerPage)) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  useEffect(() => {
    updateJsonFile();
  }, []);

  const configsForCurrentPage = configs.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
      <h2 className={"text-2x1 font-semibold mb-4"}>Configuration Editor</h2>
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
              <TableHead className="w-[100px]">ID</TableHead>
              <TableHead>Make</TableHead>
              <TableHead>Model</TableHead>
              <TableHead>Config Name*</TableHead>
              <TableHead>Seats*</TableHead>
              <TableHead>FuelType*</TableHead>
              <TableHead>Transmission*</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {configsForCurrentPage
              .filter(
                (config) =>
                  config.id.toString().includes(searchTerm) ||
                  config.car.make.includes(searchTerm) ||
                  config.car.model.includes(searchTerm) ||
                  config.car.valid.toString().includes(searchTerm) ||
                  config.car.year.toString().includes(searchTerm) ||
                  config.name.includes(searchTerm) ||
                  config.numberOfSeats.toString().includes(searchTerm) ||
                  config.fuelType.includes(searchTerm) ||
                  config.tranmissionType.includes(searchTerm)
              )
              .map((config, index) => (
                <TableRow key={index}>
                  <TableCell className="font-medium">{config.id}</TableCell>
                  <TableCell>{config.car.make}</TableCell>
                  <TableCell>{config.car.model}</TableCell>
                  <TableCell>{config.name}</TableCell>
                  <TableCell>{config.numberOfSeats}</TableCell>
                  <TableCell>{config.fuelType}</TableCell>
                  <TableCell>{config.tranmissionType}</TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
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
      </form>
      <Input type="text" placeholder="Configuration Id" value={configId} onChange={e => setConfigId(e.target.value)} />
      <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                <FormField
                    control={form.control}
                    name="name"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Name</FormLabel>
                            <FormControl>
                                <Input {...field} />
                            </FormControl>
                            <FormMessage>{form.formState.errors.name?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="numberOfSeats"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Number of Seats</FormLabel>
                            <FormControl>
                                <Input 
                                {...field} 
                                type="number"
                                onChange={e => {
                                    field.onChange(e); 
                                    form.setValue('numberOfSeats', parseInt(e.target.value));
                                }}
                                />
                            </FormControl>
                            <FormMessage>{form.formState.errors.numberOfSeats?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="fuelType"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Fuel Type</FormLabel>
                            <FormControl>
                                <Input {...field} />
                            </FormControl>
                            <FormMessage>{form.formState.errors.fuelType?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="transmissionType"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Transmission Type</FormLabel>
                            <FormControl>
                                <Input {...field} />
                            </FormControl>
                            <FormMessage>{form.formState.errors.tranmissionType?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                
                <Button type="submit">Submit</Button>
            </form>
        </Form>
    </div>
  );
}
