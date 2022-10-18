package kz.test.good.repository;

import kz.test.good.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID>, JpaSpecificationExecutor<FileInfo> {
    @Query(value = "select distinct f.contentType  " +
            "FROM FileInfo as f")
    Set<String> findAllContentTypes();

}
