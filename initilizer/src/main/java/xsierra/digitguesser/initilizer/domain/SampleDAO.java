package xsierra.digitguesser.initilizer.domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;

@Service
public class SampleDAO {

	private JdbcTemplate template;

	private static String INSERT_SAMPLE = "INSERT INTO train_sample(id, image, label) VALUES (?, ?, ?)";

	@Autowired
	public SampleDAO(JdbcTemplate template) {
		super();
		this.template = template;
	}

	public void saveSamples(List<Sample> samples) {

		template.batchUpdate(INSERT_SAMPLE, samples, 25, pss);
	}

	ParameterizedPreparedStatementSetter<Sample> pss = new ParameterizedPreparedStatementSetter<Sample>() {

		@Override
		public void setValues(PreparedStatement preparedStatement, Sample sample) throws SQLException {
			preparedStatement.setLong(1, sample.getId());

			Object[] image = Arrays.stream(sample.getImage()).mapToObj(i -> Integer.valueOf(i)).toArray();
			preparedStatement.setObject(2, image);
			preparedStatement.setInt(3, sample.getLabel());
		}
	};
}
