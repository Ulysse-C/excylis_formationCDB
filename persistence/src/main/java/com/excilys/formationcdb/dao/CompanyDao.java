package com.excilys.formationcdb.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formationcdb.dto.dao.CompanyPersist;
import com.excilys.formationcdb.dto.dao.QCompanyPersist;
import com.excilys.formationcdb.dto.dao.mapper.DaoCompanyMapper;
import com.excilys.formationcdb.dto.dao.mapper.DaoMapper;
import com.excilys.formationcdb.exception.NothingSelectedException;
import com.excilys.formationcdb.model.Company;
import com.excilys.formationcdb.model.Page;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CompanyDao {

	private EntityManager entityManager;
	
	public CompanyDao(SessionFactory sessionFactory) {
		this.entityManager = sessionFactory.createEntityManager();
	}

	public List<Company> getCompanyList() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersist companyPersist = QCompanyPersist.companyPersist;
		List<CompanyPersist> companyList = queryFactory.selectFrom(companyPersist).orderBy(companyPersist.id.asc())
				.fetch();
		return DaoCompanyMapper.toCompanyList(companyList);
	}

	public Page<Company> getPage(Page<Company> page) {
		if (page != null) {
			JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
			QCompanyPersist companyPersist = QCompanyPersist.companyPersist;
			OrderSpecifier<?> order = DaoMapper.getSortedColumn(DaoCompanyMapper.getOrder(page.getSortOrder()), page.getSortNameString());
			List<CompanyPersist> companyList = queryFactory.selectFrom(companyPersist).orderBy(order).offset(page.getOffset()).limit(page.getNumber())
					.fetch();
			page.setContent(DaoCompanyMapper.toCompanyList(companyList));
		}
		return page;
	}
	

	@Transactional
	public void deleteCompanyById(int id) throws NothingSelectedException {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersist companyPersist = QCompanyPersist.companyPersist;
		entityManager.getTransaction().begin();
		queryFactory.delete(companyPersist).where(companyPersist.id.eq(Integer.valueOf(id))).execute();
		entityManager.getTransaction().commit();
	}
}
