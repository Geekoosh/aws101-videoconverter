package com.geekoosh.aws.video.repository;

import com.geekoosh.aws.video.model.ConvertJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvertJobRepository extends CrudRepository<ConvertJob, Long> {
}
