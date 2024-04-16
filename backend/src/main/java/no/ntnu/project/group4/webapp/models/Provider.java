package no.ntnu.project.group4.webapp.models;

public class Provider {
  private Long id;
  private String name;
  private int price;

  public Provider() {
  }

  public Provider(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
