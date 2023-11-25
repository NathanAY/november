module jmp.dto {
  requires spring.data.jpa;
  requires spring.context;
  requires jakarta.persistence;
  requires static lombok;
  exports com.sungjun.dto.entity;
  exports com.sungjun.dto.repository;
}