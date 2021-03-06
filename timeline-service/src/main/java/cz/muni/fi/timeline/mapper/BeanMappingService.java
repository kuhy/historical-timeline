package cz.muni.fi.timeline.mapper;

import com.github.dozermapper.core.Mapper;
import java.util.Collection;
import java.util.List;


public interface BeanMappingService {
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();
}
