package org.cryptomator.ui.mainwindow;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import org.cryptomator.common.vaults.Vault;
import org.cryptomator.ui.addvaultwizard.AddVaultWizardComponent;
import org.cryptomator.ui.common.FxController;
import org.cryptomator.ui.common.FxControllerKey;
import org.cryptomator.ui.common.FxmlFile;
import org.cryptomator.ui.common.FxmlLoaderFactory;
import org.cryptomator.ui.common.FxmlScene;
import org.cryptomator.ui.common.StageFactory;
import org.cryptomator.ui.health.HealthCheckComponent;
import org.cryptomator.ui.migration.MigrationComponent;
import org.cryptomator.ui.removevault.RemoveVaultComponent;
import org.cryptomator.ui.stats.VaultStatisticsComponent;
import org.cryptomator.ui.vaultoptions.VaultOptionsComponent;
import org.cryptomator.ui.wrongfilealert.WrongFileAlertComponent;

import javax.inject.Provider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Map;
import java.util.ResourceBundle;

@Module(subcomponents = {AddVaultWizardComponent.class, HealthCheckComponent.class, MigrationComponent.class, RemoveVaultComponent.class, VaultOptionsComponent.class, VaultStatisticsComponent.class, WrongFileAlertComponent.class})
abstract class MainWindowModule {

	@Provides
	@MainWindowScoped
	static ObjectProperty<Vault> provideSelectedVault() {
		return new SimpleObjectProperty<>();
	}

	@Provides
	@MainWindow
	@MainWindowScoped
	static FxmlLoaderFactory provideFxmlLoaderFactory(Map<Class<? extends FxController>, Provider<FxController>> factories, MainWindowSceneFactory sceneFactory, ResourceBundle resourceBundle) {
		return new FxmlLoaderFactory(factories, sceneFactory, resourceBundle);
	}

	@Provides
	@MainWindow
	@MainWindowScoped
	static Stage provideStage(StageFactory factory) {
		Stage stage = factory.create(StageStyle.UNDECORATED);
		stage.setMinWidth(650);
		stage.setMinHeight(440);
		stage.setTitle("Cryptomator");
		return stage;
	}

	@Provides
	@FxmlScene(FxmlFile.MAIN_WINDOW)
	@MainWindowScoped
	static Scene provideMainScene(@MainWindow FxmlLoaderFactory fxmlLoaders) {
		return fxmlLoaders.createScene(FxmlFile.MAIN_WINDOW);
	}

	// ------------------

	@Binds
	@IntoMap
	@FxControllerKey(MainWindowController.class)
	abstract FxController bindMainWindowController(MainWindowController controller);

	@Binds
	@IntoMap
	@FxControllerKey(MainWindowTitleController.class)
	abstract FxController bindMainWindowTitleController(MainWindowTitleController controller);

	@Binds
	@IntoMap
	@FxControllerKey(ResizeController.class)
	abstract FxController bindResizeController(ResizeController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultListController.class)
	abstract FxController bindVaultListController(VaultListController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultListContextMenuController.class)
	abstract FxController bindVaultListContextMenuController(VaultListContextMenuController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailController.class)
	abstract FxController bindVaultDetailController(VaultDetailController controller);

	@Binds
	@IntoMap
	@FxControllerKey(WelcomeController.class)
	abstract FxController bindWelcomeController(WelcomeController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailLockedController.class)
	abstract FxController bindVaultDetailLockedController(VaultDetailLockedController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailUnlockedController.class)
	abstract FxController bindVaultDetailUnlockedController(VaultDetailUnlockedController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailMissingVaultController.class)
	abstract FxController bindVaultDetailMissingVaultController(VaultDetailMissingVaultController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailNeedsMigrationController.class)
	abstract FxController bindVaultDetailNeedsMigrationController(VaultDetailNeedsMigrationController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultDetailUnknownErrorController.class)
	abstract FxController bindVaultDetailUnknownErrorController(VaultDetailUnknownErrorController controller);

	@Binds
	@IntoMap
	@FxControllerKey(VaultListCellController.class)
	abstract FxController bindVaultListCellController(VaultListCellController controller);


}
