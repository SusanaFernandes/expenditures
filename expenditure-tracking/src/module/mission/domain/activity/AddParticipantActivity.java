package module.mission.domain.activity;

import module.mission.domain.Mission;
import module.mission.domain.MissionProcess;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import myorg.domain.User;
import myorg.util.BundleUtil;

public class AddParticipantActivity extends MissionProcessActivity<MissionProcess, ParticipantActivityInformation> {

    @Override
    public String getLocalizedName() {
	return BundleUtil.getStringFromResourceBundle("resources/MissionResources", "activity." + getClass().getSimpleName());
    }

    @Override
    public boolean isActive(final MissionProcess missionProcess, final User user) {
	return super.isActive(missionProcess, user) && missionProcess.isUnderConstruction() && missionProcess.isRequestor(user);
    }

    @Override
    protected void process(final ParticipantActivityInformation participantActivityInformation) {
	final MissionProcess missionProcess = participantActivityInformation.getProcess();
	final Mission mission = missionProcess.getMission();
	final Person person = participantActivityInformation.getPerson();
	mission.addParticipantes(person);
    }

    @Override
    public ActivityInformation<MissionProcess> getActivityInformation(final MissionProcess process) {
	return new ParticipantActivityInformation(process, this);
    }

    @Override
    public boolean isVisible() {
        return false;
    }

}
