package no.ntnu.project.group4.webapp.models;

public class ExtraFeature {
  private Long id;
  private String name;

  public ExtraFeature() {
  }

  public ExtraFeature(String name) {
    this.name = name;
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
}
