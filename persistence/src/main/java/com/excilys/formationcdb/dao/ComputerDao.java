package com.excilys.formationcdb.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.formationcdb.dto.dao.ComputerPersist;
import com.excilys.formationcdb.dto.dao.QComputerPersist;
import com.excilys.formationcdb.dto.dao.mapper.DaoCompanyMapper;
import com.excilys.formationcdb.dto.dao.mapper.DaoComputerMapper;
import com.excilys.formationcdb.dto.dao.mapper.DaoMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Computer;
import com.excilys.formationcdb.model.Page;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ComputerDao {

	private EntityManager entityManager;
	private QComputerPersist computerPersist;
	private JPAQueryFactory queryFactory;

	public ComputerDao(SessionFactory sessionFactory) {
		this.entityManager = sessionFactory.createEntityManager();
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.computerPersist = QComputerPersist.computerPersist;
	}

	public Optional<Computer> getComputerById(int id) {
		ComputerPersist computer = queryFactory.selectFrom(computerPersist)
				.where(computerPersist.id.eq(Integer.valueOf(id))).fetchOne();
		return Optional.ofNullable(DaoComputerMapper.toComputer(computer));
	}

	public void createComputer(Computer computer) {
		if (computer != null) {
			ComputerPersist computerPersist = DaoComputerMapper.toComputerPersist(computer);
			entityManager.getTransaction().begin();
			entityManager.merge(computerPersist);
			entityManager.getTransaction().commit();
		}
	}

	public void deleteComputerById(int id) throws NothingSelectedException {
		entityManager.getTransaction().begin();
		queryFactory.delete(computerPersist).where(computerPersist.id.eq(Integer.valueOf(id))).execute();
		entityManager.getTransaction().commit();
	}

	public int getComputerNumberbyName(String search) {
		return (int) queryFactory.selectFrom(computerPersist).where(computerPersist.name.containsIgnoreCase(search)).fetchCount();
	}

	public Page<Computer> getPage(Page<Computer> page) {
		if (page != null) {
			OrderSpecifier<?> order = DaoMapper.getSortedColumn(DaoComputerMapper.getOrder(page.getSortOrder()),
					page.getSortNameString());
			List<ComputerPersist> computerList = queryFactory.selectFrom(computerPersist).orderBy(order)
					.offset(page.getOffset()).limit(page.getSize())
					.where(computerPersist.name.containsIgnoreCase(page.getSearch())).fetch();
			page.setContent(DaoComputerMapper.toComputerList(computerList));
		}
		return page;
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		if (computer != null) {
			entityManager.getTransaction().begin();
			queryFactory.update(computerPersist).where(computerPersist.id.eq(Integer.valueOf(computer.getId())))
					.set(computerPersist.name, computer.getName())
					.set(computerPersist.introduced, computer.getIntroduced())
					.set(computerPersist.discontinued, computer.getDiscontinued())
					.set(computerPersist.company, DaoCompanyMapper.toCompanyPersist( computer.getCompany())).execute();
			entityManager.getTransaction().commit();
		}
	}

}
