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
    location: z.string(),
    price: z.number(),
    visible: z.boolean(), 
    available: z.boolean(),

  });

export default function ProviderEditor() {
  const [cars, setCars] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [providers, setProviders] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const entriesPerPage = 5;
  const [providerId, setProviderId] = useState('');

  const form = useForm({
    resolver: zodResolver(formSchema),
        defaultValues: {
        name: '',
        location: '',
        price: '',
        visible: false,
        available: false
    }
  });

  const onSubmit = async (values)  => {
 
    try {
        const responseProviders = await asyncApiRequest("PUT", `/api/providers/${providerId}`, values, true);
 
    
        if (responseProviders.ok && responseVisibility.ok) {
        } else {
            if (!responseProviders.ok) {
                console.error("Error updating provider details: ", responseProviders);
            }
            if (!responseVisibility.ok) {
                console.error("Error updating visibility details: ", responseVisibility);
            }
        }
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

      const allProviders = data.flatMap((car) =>
        car.configurations.flatMap((config) =>
          config.providers.map((provider) => ({ ...provider, car, config }))
        )
      );
      setProviders(allProviders);
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

  const carsForCurrentPage = providers.slice(
    (currentPage - 1) * entriesPerPage,
    currentPage * entriesPerPage
  );

  return (
    <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
      <h2 className={"text-2x1 font-semibold mb-4"}>Provider Editor</h2>
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
              <TableHead className="w-[100px]">Provider ID</TableHead>
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
              .filter(
                (provider) =>
                  provider.id.toString() === searchTerm ||
                  provider.car.make.includes(searchTerm) ||
                  provider.car.model.includes(searchTerm) ||
                  provider.config.name.includes(searchTerm) ||
                  provider.name.includes(searchTerm) ||
                  provider.price.toString().includes(searchTerm) ||
                  provider.location.includes(searchTerm) ||
                  provider.visible.toString().includes(searchTerm) ||
                  provider.available.toString().includes(searchTerm)
              )
              .sort((a, b) => a.id - b.id)
              .map((provider, index) => (
                <TableRow key={index}>
                  <TableCell className="font-medium">{provider.id}</TableCell>
                  <TableCell>{provider.car.make}</TableCell>
                  <TableCell>{provider.car.model}</TableCell>
                  <TableCell>{provider.config.name}</TableCell>
                  <TableCell>{provider.name}</TableCell>
                  <TableCell>{provider.location}</TableCell>
                  <TableCell>{provider.price + " kr"}</TableCell>
                  <TableCell>{provider.visible.toString()}</TableCell>
                  <TableCell>{provider.available.toString()}</TableCell>
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
      <Input type="text" placeholder="Provider Id" value={providerId} onChange={e => setProviderId(e.target.value)} />
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
                    name="location"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Location</FormLabel>
                            <FormControl>
                                <Input {...field} />
                            </FormControl>
                            <FormMessage>{form.formState.errors.location?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="price"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Price</FormLabel>
                            <FormControl>
                                <Input 
                                {...field} 
                                type="number"
                                onChange={e => {
                                    field.onChange(e); 
                                    form.setValue('price', parseFloat(e.target.value));
                                }}
                                />
                            </FormControl>
                            <FormMessage>{form.formState.errors.price?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="visible"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Visible</FormLabel>
                            <FormControl>
                                <Input 
                                {...field} 
                                type="checkbox"
                                onChange={e => {
                                    field.onChange(e); 
                                    form.setValue('visible', e.target.checked);
                                }}
                                />
                            </FormControl>
                            <FormMessage>{form.formState.errors.visible?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="available"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Available</FormLabel>
                            <FormControl>
                                <Input 
                                {...field} 
                                type="checkbox"
                                onChange={e => {
                                    field.onChange(e); 
                                    form.setValue('available', e.target.checked);
                                }}
                                />
                            </FormControl>
                            <FormMessage>{form.formState.errors.available?.message}</FormMessage>
                        </FormItem>
                    )}
                />
                       
                <Button type="submit">Submit</Button>
            </form>
        </Form>
    </div>
  );
}
