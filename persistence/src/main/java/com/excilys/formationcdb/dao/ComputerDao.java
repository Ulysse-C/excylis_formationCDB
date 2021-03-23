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

	public ComputerDao(SessionFactory sessionFactory) {
		this.entityManager = sessionFactory.createEntityManager();
	}

	public Optional<Computer> getComputerById(int id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersist computerPersist = QComputerPersist.computerPersist;
		ComputerPersist computer = queryFactory.selectFrom(computerPersist)
				.where(computerPersist.id.eq(Integer.valueOf(id))).fetchOne();
		return Optional.ofNullable(DaoComputerMapper.toComputer(computer));
	}

	public void createComputer(Computer computer) {
		if (computer != null) {
			ComputerPersist computerPersist = DaoComputerMapper.toComputerPersist(computer);
			entityManager.getTransaction().begin();
			entityManager.persist(computerPersist);
			entityManager.getTransaction().commit();
		}
	}

	public void deleteComputerById(int id) throws NothingSelectedException {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersist computerPersist = QComputerPersist.computerPersist;
		entityManager.getTransaction().begin();
		queryFactory.delete(computerPersist).where(computerPersist.id.eq(Integer.valueOf(id))).execute();
		entityManager.getTransaction().commit();
	}

	public int getComputerNumberbyName(String search) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersist computerPersist = QComputerPersist.computerPersist;
		return (int) queryFactory.selectFrom(computerPersist).where(computerPersist.name.contains(search)).fetchCount();
	}

	public Page<Computer> getPage(Page<Computer> page) {
		if (page != null) {
			JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
			QComputerPersist computerPersist = QComputerPersist.computerPersist;
			OrderSpecifier<?> order = DaoMapper.getSortedColumn(DaoComputerMapper.getOrder(page.getSortOrder()),
					page.getSortNameString());
			List<ComputerPersist> computerList = queryFactory.selectFrom(computerPersist).orderBy(order)
					.offset(page.getOffset()).limit(page.getSize()).fetch();
			page.setContent(DaoComputerMapper.toComputerList(computerList));
		}
		return page;
	}

	public void updateComputer(Computer computer) throws NothingSelectedException {
		if (computer != null) {
			JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
			QComputerPersist computerPersist = QComputerPersist.computerPersist;
			entityManager.getTransaction().begin();
			queryFactory.update(computerPersist).where(computerPersist.id.eq(Integer.valueOf(computer.getId())))
					.set(computerPersist.name, computer.getName())
					.set(computerPersist.introduced, computer.getIntroduced())
					.set(computerPersist.discontinued, computer.getDiscontinued())
					.set(computerPersist.companyId, computer.getCompanyId()).execute();
			entityManager.getTransaction().commit();
//			SqlParameterSource params = new BeanPropertySqlParameterSource(computer);
//			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//			int row = jdbcTemplate.update(UPDATE_COMPUTER_QUERY, params);
//			if (row == 0) {
//				throw new NothingSelectedException("Update");
//			}
		}
	}

}
